package com.retarus.fax.base;

import com.retarus.fax.base.rest.URLProvider;
import com.retarus.fax.RetarusFax;
import com.retarus.fax.http.Fax4ApplApiClient;

import java.util.concurrent.CompletableFuture;

/**
 * @param <V> The type of the data to be fetched.
 * @param <T> The type of the result of the fetching.
 * @author thiagon<p>
 * Abstract class for the fetching of data.
 */
public abstract class Retriever<V, T> {

    protected final Fax4ApplApiClient apiClient;
    protected final URLProvider urlProvider;

    protected Retriever(Fax4ApplApiClient apiClient, URLProvider urlProvider) {
        this.apiClient = apiClient;
        this.urlProvider = urlProvider;
    }

    public CompletableFuture<T> getAsync(V data) {
        return CompletableFuture.supplyAsync(() -> get(data), RetarusFax.getExecutorService());
    }

    public abstract T get(V data);
}
