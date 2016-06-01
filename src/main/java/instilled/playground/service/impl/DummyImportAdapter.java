package instilled.playground.service.impl;

import instilled.playground.service.Configuration;
import instilled.playground.service.IEntity;
import instilled.playground.service.IImportAdapter;
import instilled.playground.service.IImportService;

public class DummyImportAdapter implements IImportAdapter {

    private IImportService _importService;

    @Override
    public void initialize(Configuration configuration) {
        System.out.println("Initializing DummyImportAdapter");

        _importService = configuration.importService();
    }

    @Override
    public void start() {
//        configuration.scheduler().submit();
        System.out.println("start ...");
        _importService.importEntities(new String[] {"1", "2"}, new IEntity[] {new IEntity() {
            @Override
            public String id() {
                return "id1";
            }

            @Override
            public String field1() {
                return "field1";
            }

            @Override
            public String field2() {
                return "field2";
            }

            @Override
            public String field3() {
                return "field3";
            }
        }});
    }

    @Override
    public void stop() {

    }
}
