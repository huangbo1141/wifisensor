package robin.com.wifisensor.model;

/**
 * Created by Bhadresh Chavada on 22-02-2017.
 */

public class QuizResult {

    String GSR7="0.0", GSR5="0.0", GAR10="0.0", GSR12="0.0", GSR15="0.0";
    int selectedPos=0, RandomPos=0;
    public float nCount=0;
    boolean result;

    public QuizResult() {
    }

    public String getGSR7() {
        return GSR7;
    }

    public void setGSR7(String GSR7) {
        this.GSR7 = GSR7;
    }

    public String getGSR5() {
        return GSR5;
    }

    public void setGSR5(String GSR5) {
        this.GSR5 = GSR5;
    }

    public String getGAR10() {
        return GAR10;
    }

    public void setGAR10(String GAR10) {
        this.GAR10 = GAR10;
    }

    public String getGSR12() {
        return GSR12;
    }

    public void setGSR12(String GSR12) {
        this.GSR12 = GSR12;
    }

    public String getGSR15() {
        return GSR15;
    }

    public void setGSR15(String GSR15) {
        this.GSR15 = GSR15;
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    public int getRandomPos() {
        return RandomPos;
    }

    public void setRandomPos(int randomPos) {
        RandomPos = randomPos;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void genResult(){
        if (selectedPos == RandomPos) {
            this.result = true;

        } else {
            this.result = false;
        }
    }
}
