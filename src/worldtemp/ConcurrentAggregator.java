package worldtemp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ConcurrentAggregator {

    public static final int N_THREADS = 4;
    private List<Future<CityWithAgg>> futures = new ArrayList<>();

    ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

    public void addCityAggregation(Callable<CityWithAgg> task) {
        futures.add(executorService.submit(task));
    }

    public List<CityWithAgg> getCityWithAggList() {
        List<CityWithAgg> citiesWithAgg = new ArrayList<>();
        while (!futures.isEmpty()) {
            for (Future<CityWithAgg> f : futures) {
                if (f.isDone()) {
                    try {
                        citiesWithAgg.add(f.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
            futures = futures.stream().filter((future) -> !future.isDone()).collect(Collectors.toList());
        }
        executorService.shutdown();
        executorService
        return citiesWithAgg;
    }
}
