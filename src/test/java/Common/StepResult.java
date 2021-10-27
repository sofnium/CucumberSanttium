package Common;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class StepResult {
    private String idTestRun;
    private String idStep;
    private String data;
    private String observations;
    private int Status;
    private String stringFile;
    private String video;
}
