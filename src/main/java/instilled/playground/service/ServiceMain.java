package instilled.playground.service;

import java.util.Iterator;
import java.util.List;

public class ServiceMain {
    public static void main(String[] args) {

        List<IImportAdapter> adapters = ImportAdapterService.getInstance().loadAll();

        final Configuration cfg = new Configuration() {
            @Override
            public IImportService importService() {
                return new IImportService() {
                    @Override
                    public void importEntities(String[] cols, IEntity[] entity) {
                        System.out.printf("importing[id:%s]", entity[0].id());
                    }
                };
            }
        };

        for (IImportAdapter adapter : adapters) {
            adapter.initialize(cfg);
            adapter.start();
        }
    }
}
