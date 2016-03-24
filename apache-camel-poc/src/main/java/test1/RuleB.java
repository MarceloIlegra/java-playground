package test1;

public class RuleB implements IRule{
    @Override
    public void execute(String text) {
        System.out.println("STEP 2" + text);
    }
}
