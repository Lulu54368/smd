package strategy;

public class StrategyFactory {
    private static StrategyFactory instance = new StrategyFactory();



    //singleton
    public static StrategyFactory getInstance() {
        if(instance == null){
            instance = new StrategyFactory();
        }
        return instance;
    }
    public IStrategy getStrategy(String name){
        IStrategy strategy = null;
        switch(name){
            case "random":
                strategy= new RandomStrategy();
            case "legal":
                strategy= new LegalStrategy();
            case "smart":
                strategy= new SmartStrategy();
        }
        return strategy;
    }

}
