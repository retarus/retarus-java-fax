package com.retarus.fax.http;

import com.retarus.fax.base.rest.URLProvider;

public interface Fax4ApplApiClient extends ApiClient, Fax4ApplClient {

    URLProvider getLocale();
}
