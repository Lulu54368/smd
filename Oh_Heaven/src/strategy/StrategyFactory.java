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
                return new RandomStrategy();
            case "legal":
                return  new LegalStrategy();
            case "smart":
                return new SmartStrategy();
        }
        return strategy;
    }

}
