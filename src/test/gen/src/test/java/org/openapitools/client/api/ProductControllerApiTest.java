/*
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.GlobalExceptionDTO;
import org.openapitools.client.model.InvalidInputValuesExceptionDTO;
import org.openapitools.client.model.NotFoundExceptionDTO;
import org.openapitools.client.model.ProductRequestDTO;
import org.openapitools.client.model.ProductResponseDTO;
import org.openapitools.client.model.UploadFileRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ProductControllerApi
 */
@Disabled
public class ProductControllerApiTest {

    private final ProductControllerApi api = new ProductControllerApi();

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addTest() throws ApiException {
        String currency = null;
        ProductRequestDTO productRequestDTO = null;
        ProductResponseDTO response = api.add(currency, productRequestDTO);
        // TODO: test validations
    }

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void allProductStoreRelatedTest() throws ApiException {
        String currency = null;
        String id = null;
        List<ProductResponseDTO> response = api.allProductStoreRelated(currency, id);
        // TODO: test validations
    }

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteByIdTest() throws ApiException {
        String id = null;
        api.deleteById(id);
        // TODO: test validations
    }

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteManyTest() throws ApiException {
        List<String> requestBody = null;
        api.deleteMany(requestBody);
        // TODO: test validations
    }

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAllTest() throws ApiException {
        String currency = null;
        List<ProductResponseDTO> response = api.getAll(currency);
        // TODO: test validations
    }

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getByIdTest() throws ApiException {
        String id = null;
        String currency = null;
        ProductResponseDTO response = api.getById(id, currency);
        // TODO: test validations
    }

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getLastTest() throws ApiException {
        String last = null;
        String currency = null;
        ProductResponseDTO response = api.getLast(last, currency);
        // TODO: test validations
    }

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateTest() throws ApiException {
        String id = null;
        String currency = null;
        ProductRequestDTO productRequestDTO = null;
        ProductResponseDTO response = api.update(id, currency, productRequestDTO);
        // TODO: test validations
    }

    /**
     * @throws ApiException if the Api call fails
     */
    @Test
    public void uploadFileTest() throws ApiException {
        UploadFileRequest uploadFileRequest = null;
        api.uploadFile(uploadFileRequest);
        // TODO: test validations
    }

}
