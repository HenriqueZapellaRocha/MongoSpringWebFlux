# ProductControllerApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**add**](ProductControllerApi.md#add) | **POST** /product/add |  |
| [**allProductStoreRelated**](ProductControllerApi.md#allProductStoreRelated) | **GET** /product/storeRelated/{id} |  |
| [**deleteById**](ProductControllerApi.md#deleteById) | **DELETE** /product/{id} |  |
| [**deleteMany**](ProductControllerApi.md#deleteMany) | **DELETE** /product |  |
| [**getAll**](ProductControllerApi.md#getAll) | **GET** /product/All |  |
| [**getById**](ProductControllerApi.md#getById) | **GET** /product/{id} |  |
| [**getLast**](ProductControllerApi.md#getLast) | **GET** /product/last |  |
| [**update**](ProductControllerApi.md#update) | **PUT** /product/{id} |  |
| [**uploadFile**](ProductControllerApi.md#uploadFile) | **POST** /product/uploadProductImage |  |


<a name="add"></a>
# **add**
> ProductResponseDTO add(currency, productRequestDTO)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductControllerApi apiInstance = new ProductControllerApi(defaultClient);
    String currency = "currency_example"; // String | 
    ProductRequestDTO productRequestDTO = new ProductRequestDTO(); // ProductRequestDTO | 
    try {
      ProductResponseDTO result = apiInstance.add(currency, productRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductControllerApi#add");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **currency** | **String**|  | |
| **productRequestDTO** | [**ProductRequestDTO**](ProductRequestDTO.md)|  | |

### Return type

[**ProductResponseDTO**](ProductResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |

<a name="allProductStoreRelated"></a>
# **allProductStoreRelated**
> List&lt;ProductResponseDTO&gt; allProductStoreRelated(currency, id)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductControllerApi apiInstance = new ProductControllerApi(defaultClient);
    String currency = "currency_example"; // String | 
    String id = "id_example"; // String | 
    try {
      List<ProductResponseDTO> result = apiInstance.allProductStoreRelated(currency, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductControllerApi#allProductStoreRelated");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **currency** | **String**|  | |
| **id** | **String**|  | |

### Return type

[**List&lt;ProductResponseDTO&gt;**](ProductResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |

<a name="deleteById"></a>
# **deleteById**
> deleteById(id)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductControllerApi apiInstance = new ProductControllerApi(defaultClient);
    String id = "id_example"; // String | 
    try {
      apiInstance.deleteById(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductControllerApi#deleteById");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |

<a name="deleteMany"></a>
# **deleteMany**
> deleteMany(requestBody)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductControllerApi apiInstance = new ProductControllerApi(defaultClient);
    List<String> requestBody = Arrays.asList(); // List<String> | 
    try {
      apiInstance.deleteMany(requestBody);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductControllerApi#deleteMany");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **requestBody** | [**List&lt;String&gt;**](String.md)|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |

<a name="getAll"></a>
# **getAll**
> List&lt;ProductResponseDTO&gt; getAll(currency)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductControllerApi apiInstance = new ProductControllerApi(defaultClient);
    String currency = "currency_example"; // String | 
    try {
      List<ProductResponseDTO> result = apiInstance.getAll(currency);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductControllerApi#getAll");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **currency** | **String**|  | |

### Return type

[**List&lt;ProductResponseDTO&gt;**](ProductResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |

<a name="getById"></a>
# **getById**
> ProductResponseDTO getById(id, currency)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductControllerApi apiInstance = new ProductControllerApi(defaultClient);
    String id = "id_example"; // String | 
    String currency = "currency_example"; // String | 
    try {
      ProductResponseDTO result = apiInstance.getById(id, currency);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductControllerApi#getById");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |
| **currency** | **String**|  | |

### Return type

[**ProductResponseDTO**](ProductResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |

<a name="getLast"></a>
# **getLast**
> ProductResponseDTO getLast(last, currency)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductControllerApi apiInstance = new ProductControllerApi(defaultClient);
    String last = "last_example"; // String | 
    String currency = "currency_example"; // String | 
    try {
      ProductResponseDTO result = apiInstance.getLast(last, currency);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductControllerApi#getLast");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **last** | **String**|  | |
| **currency** | **String**|  | |

### Return type

[**ProductResponseDTO**](ProductResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |

<a name="update"></a>
# **update**
> ProductResponseDTO update(id, currency, productRequestDTO)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductControllerApi apiInstance = new ProductControllerApi(defaultClient);
    String id = "id_example"; // String | 
    String currency = "currency_example"; // String | 
    ProductRequestDTO productRequestDTO = new ProductRequestDTO(); // ProductRequestDTO | 
    try {
      ProductResponseDTO result = apiInstance.update(id, currency, productRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductControllerApi#update");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |
| **currency** | **String**|  | |
| **productRequestDTO** | [**ProductRequestDTO**](ProductRequestDTO.md)|  | |

### Return type

[**ProductResponseDTO**](ProductResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |

<a name="uploadFile"></a>
# **uploadFile**
> uploadFile(uploadFileRequest)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductControllerApi apiInstance = new ProductControllerApi(defaultClient);
    UploadFileRequest uploadFileRequest = new UploadFileRequest(); // UploadFileRequest | 
    try {
      apiInstance.uploadFile(uploadFileRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductControllerApi#uploadFile");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **uploadFileRequest** | [**UploadFileRequest**](UploadFileRequest.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |

