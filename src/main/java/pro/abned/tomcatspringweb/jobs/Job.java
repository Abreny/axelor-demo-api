package pro.abned.tomcatspringweb.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Job {
    private final SyncProduct syncProduct;

    public Job(SyncProduct syncProduct) {
        this.syncProduct = syncProduct;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void syncProduct() {
        new Thread(this.syncProduct::start).start();
    }
}
