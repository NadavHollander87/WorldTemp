package worldtemp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentAggregator {

    public static final int N_THREADS = 4;
    private final List<Callable<CityWithAgg>> aggsToCalc = new ArrayList<>();

    ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

    public void addCityAggregation(Callable<CityWithAgg> task) {
        aggsToCalc.add(task);
    }

    public List<CityWithAgg> getCityWithAggList() {
        List<Future<CityWithAgg>> futuresOfcitiesWithAgg;
        List<CityWithAgg> citiesWithAgg = new ArrayList<>();
        try {
            futuresOfcitiesWithAgg = executorService.invokeAll(aggsToCalc);
            for (Future<CityWithAgg> f : futuresOfcitiesWithAgg) {
                citiesWithAgg.add(f.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        return citiesWithAgg;
    }
}
