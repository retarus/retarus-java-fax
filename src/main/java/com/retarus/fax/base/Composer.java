package com.retarus.fax.base;

import com.retarus.fax.RetarusFax;
import com.retarus.fax.http.Fax4ApplApiClient;

import java.util.concurrent.CompletableFuture;

/**
 * @param <V> The type of the data to be generated.
 * @param <T> The type of the result of the generation.
 * @author thiagon<p>
 * Abstract class for the generation of data.
 */
public abstract class Composer<V, T> {

    protected final Fax4ApplApiClient apiClient;

    protected Composer(Fax4ApplApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public CompletableFuture<T> composeAsync(V data) {
        return CompletableFuture.supplyAsync(() -> compose(data), RetarusFax.getExecutorService());
    }

    public abstract T compose(V data);
}
