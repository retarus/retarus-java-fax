package com.retarus.fax.base;

import com.retarus.fax.base.rest.URLProvider;
import com.retarus.fax.RetarusFax;
import com.retarus.fax.http.Fax4ApplApiClient;

import java.util.concurrent.CompletableFuture;

/**
 * @param <V> The type of the data to be deleted.
 * @param <T> The type of the result of the deletion.
 * @author thiagon<p>
 * Abstract class for the deletion of data.
 */
public abstract class Deleter<V, T> {

    protected final Fax4ApplApiClient apiClient;
    protected final URLProvider urlProvider;

    protected Deleter(Fax4ApplApiClient apiClient, URLProvider urlProvider) {
        this.apiClient = apiClient;
        this.urlProvider = urlProvider;
    }

    public CompletableFuture<T> deleteAsync(V data) {
        return CompletableFuture.supplyAsync(() -> delete(data), RetarusFax.getExecutorService());
    }

    public abstract T delete(V data);
}
