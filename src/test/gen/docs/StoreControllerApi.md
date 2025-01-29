# StoreControllerApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getRelatedStores**](StoreControllerApi.md#getRelatedStores) | **GET** /store/all |  |
| [**getStoreById**](StoreControllerApi.md#getStoreById) | **GET** /store/{id} |  |


<a name="getRelatedStores"></a>
# **getRelatedStores**
> List&lt;StoreResponseDTO&gt; getRelatedStores()



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StoreControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    StoreControllerApi apiInstance = new StoreControllerApi(defaultClient);
    try {
      List<StoreResponseDTO> result = apiInstance.getRelatedStores();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StoreControllerApi#getRelatedStores");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;StoreResponseDTO&gt;**](StoreResponseDTO.md)

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

<a name="getStoreById"></a>
# **getStoreById**
> StoreResponseDTO getStoreById(id)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StoreControllerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    StoreControllerApi apiInstance = new StoreControllerApi(defaultClient);
    String id = "id_example"; // String | 
    try {
      StoreResponseDTO result = apiInstance.getStoreById(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StoreControllerApi#getStoreById");
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

[**StoreResponseDTO**](StoreResponseDTO.md)

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

