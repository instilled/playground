package instilled.playground.service;

import java.util.*;

public class ImportAdapterService {

    private static ImportAdapterService _service;
    private final ServiceLoader<IImportAdapter> _loader;

    private ImportAdapterService() {
        _loader = ServiceLoader.load(IImportAdapter.class
                // TODO: fb: add custom classloader
                );
    }

    public static synchronized ImportAdapterService getInstance() {
        if (_service == null) {
            _service = new ImportAdapterService();
        }
        return _service;
    }

    public List<IImportAdapter> loadAll() {
        List<IImportAdapter> adapters = new ArrayList<IImportAdapter>(4);
        try {

            Iterator<IImportAdapter> it = _loader.iterator();
            while (it.hasNext()) {
                adapters.add(it.next());
            }
        } catch (ServiceConfigurationError serviceError) {
            throw new IllegalStateException("Failed to load import adapters!");
        }
        return adapters;
    }
}