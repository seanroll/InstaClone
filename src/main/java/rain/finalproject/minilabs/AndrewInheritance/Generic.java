package rain.finalproject.minilabs.AndrewInheritance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Generic {
    public final String masterType = "Generic";
    private String type;

    public abstract String toString();
}
