class GraphicsElement{

    posX;
    posY;
    sizeX;
    sizeY;
    displayColor;

    constructor(posX, posY, sizeX, sizeY, displayColor){
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.displayColor = displayColor;
    }

    setPosition(posX, posY){
        this.posX = posX;
        this.posY = posY;
    }

    setSize(sizeX, sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    get getposX(){return this.posX}
    get getposY(){return this.posY}
    get getsizeX(){return this.sizeX}
    get getsizeY(){return this.sizeY}

}

class GameObject extends GraphicsElement{

    velX;
    velY;
    accelX;
    accelY;

    constructor(posX, posY, sizeX, sizeY, displayColor) {
        super(posX, posY, sizeX, sizeY, displayColor);
    }

    setVelocity(vX, vY){
        this.velX = vX;
        this.velY = vY;
    }

    setAcceleration(aX, aY){
        this.accelX = aX;
        this.accelY = aY;
    }

    getCollidingObject(otherObjects){
        for(let i=0; i < otherObjects.length(); i++ ){
            let other = otherObjects[i];
            if (this != other &&
                this.getposX < other.getposX + other.getsizeX &&
                this.getposX + this.getsizeX > other.getposX &&
                this.getposY < other.getposY + other.getsizeY &&
                this.getposY + this.getsizeY > other.getposY)
            {
                return other;
            }
        }
        return null;
    }

    updatePhysics(timeDelta){
        this.setVelocity(this.velX + (this.accelX * timeDelta), this.velY + (this.accelY * timeDelta))
        //todo make bullet immune from screen wrapping
        if(this.accelY!=0){
            this.setPosition(((500 + this.getposX) % 500) + this.velX * timeDelta , this.getposY + this.velY * timeDelta)
        }
        else {this.setPosition(this.getposX + this.velX * timeDelta , this.getposY + this.velY * timeDelta)}
    }


    get getvelX(){return this.velX;}
    get getvelY(){return this.velY;}
    get getaccelX(){return this.accelX;}
    get getaccelY(){return this.accelY;}
}

class Player extends GameObject{

    constructor(posX, posY) {
        super(posX, posY, 25, 25, "(22, 139, 3)");
        this.INITIAL_VELY = 0;
        this.ACCEL_Y = 263;
        this.setVelocity(0, this.INITIAL_VELY);
        this.setAcceleration(0,this.ACCEL_Y);
    }
}

class Platform extends GameObject{
    constructor(posX, posY, bounceY) {
        super(posX, posY, 100, 25, "(95, 95, 95)");
        this.bounceVelY = bounceY;
    }
}

class Monster extends GameObject {
    constructor(posX, posY, bounceY) {
        super(posX, posY, 75, 75, "(3, 123, 139)");
        this.bounceVelY = bounceY;
    }
}

class Bullet extends GameObject{

    constructor(posX, posY) {
        super(posX, posY, 10, 10, "(100, 72, 0)");
        this.accelY = 0;
        this.accelX = 0;
    }
}





let player;
let bullet;
let monsters;
let platforms;

let leftKeyDown = false;
let rightKeyDown = false;

let BULLET_SPEED = 400;
let canShoot = true;
let dx = 0;
let dy = 0;

let ctx = document.getElementById('game window').getContext('2d');

function init() {

    //initializes canvas
    let canvas = document.getElementById('game window');
    if (canvas.getContext) {

        isRunning = true;

        //puts objects in initial position
        player  = new Player(250, 200)
        bullet = []
        monsters = [new Monster(50,200), new Monster(50,400),new Monster(400,300),0]
        platforms = [new Platform(400,400), new Platform(400,200), new Platform(250,500), new Platform(100,300)]

        //adds button and mouse event listeners
        canvas.addEventListener('click', function(event) {
                if (canShoot){
                    lastDownTarget = event.target;

                    canShoot = false;

                    dx = -(canvas.getBoundingClientRect().x - (event.clientX - player.getposX));
                    dy = -(canvas.getBoundingClientRect().y - (event.clientY - player.getposY));

                    bullet[0] = new Bullet(player.getposX, player.getposY);
                    bullet[0].setVelocity(BULLET_SPEED * dx / Math.hypot(dx, dy),  BULLET_SPEED * dy / Math.hypot(dx, dy));}

            }
            , false);

        document.addEventListener('keydown', function(event) {
            if(lastDownTarget == canvas) {
                if(event.key == "ArrowLeft"){
                    leftKeyDown = true;
                }
                if (event.key == "ArrowRight"){
                    rightKeyDown = true;
                }
            }
        }, false);

        document.addEventListener('keyup', function(event) {
            if(lastDownTarget == canvas) {

                if(event.key == "ArrowLeft"){
                    leftKeyDown = false;
                }
                if (event.key == "ArrowRight"){
                    rightKeyDown = false;
                }
            }
        }, false);

        requestAnimationFrame(draw)}

}


//updated model based on surroundings
function newState(){


    //checks monster collision with player and bullet
    m = monsters;
    for(i = 0; i < m.length; i++){

        //player
        if (player.getposX + player.sizeX > m[i].getposX && player.getposX < m[i].getposX + m[i].sizeX &&
            player.getposY + player.sizeY > m[i].getposY && player.getposY < m[i].getposY + m[i].getsizeY) {
            //if jumping on, monster dies, acts as a platform
            if (player.getvelY >=0) {
                monsters[i] = 0;
                player.setVelocity(player.getvelX, -324);
            }

            //if jumping into, game over
            else {if (player.getvelY  <= 0){
                gameOver();
            }}
        }

        //bullet
        if(bullet[0] && Math.abs(250 - bullet[0].getposX) >= 250){
            bullet[0] = 0;
            canShoot = true;
        }
        if (bullet[0] &&
            bullet[0].getposX + bullet[0].sizeX > m[i].getposX && bullet[0].getposX < m[i].getposX + m[i].sizeX &&
            bullet[0].getposY + bullet[0].sizeY > m[i].getposY  && bullet[0].getposY < m[i].getposY + m[i].getsizeY){
            monsters[i] = 0;
        }
    }

    //checks platform collision with player
    for(i = 0; i < platforms.length; i++){
        if (player.getvelY  >= 0){

            if (player.getposX + player.sizeX >= platforms[i].getposX && player.getposX <= platforms[i].getposX + platforms[i].sizeX &&
                player.getposY + player.sizeY >= platforms[i].getposY && player.getposY <= platforms[i].getposY + platforms[i].getsizeY)
            {player.setVelocity(player.getvelX, -324);}
        }

    }

    //checks player falling out of world
    if (player.getposY >= 600){
        gameOver();
    }



    //key pressed logic
    if (rightKeyDown){
        if (leftKeyDown){player.setVelocity(0, player.getvelY);}
        else {player.setVelocity(130, player.getvelY);}
    }
    else if (leftKeyDown){player.setVelocity(-130, player.getvelY)}
    else{player.setVelocity(0, player.getvelY);}


    //moves and updates position of velocity of all objects
    player.updatePhysics(1/60);
    if(bullet[0]){bullet[0].updatePhysics(1/60); //alert("physics of bullet updated"); alert(`position is currently${bullet[0].getposX}, ${bullet[0].getposY}`)
    }


}


//code if one dies
function gameOver(){
    isRunning = false;
    alert("You Died! Refresh the page to play again.");
}


//draws graphicsobject
function drawObject(obj){
    let ctx = document.getElementById('game window').getContext('2d');
    ctx.globalCompositeOperation = 'destination-over';
    ctx.fillStyle = `rgb${obj.displayColor}`;
    ctx.fillRect(obj.getposX, obj.getposY, obj.getsizeX, obj.getsizeY);
}


//updates the view
function draw() {

    let ctx = document.getElementById('game window').getContext('2d');
    ctx.globalCompositeOperation = 'destination-over';
    ctx.clearRect(0, 0, 500, 600); // clear canvas

    if(isRunning){
        newState();

        drawObject(player);

        if (bullet[0]){drawObject(bullet[0]); //alert(`drew bullet at ${bullet[0].getposX}, ${bullet[0].getposY}`);
        }

        if(monsters){for (let i = 0; i < monsters.length; i++) {
            drawObject(monsters[i]);
        }}

        for (let i = 0; i < platforms.length; i++) {
            drawObject(platforms[i]);
        }
    }
    else {
        return;
    }

    requestAnimationFrame(draw);
}