package com.adriandev.products.services;

import com.adriandev.products.model.dtos.ProductRequest;
import com.adriandev.products.model.dtos.ProductResponse;
import com.adriandev.products.model.entities.Product;
import com.adriandev.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Marca la clase como componente de servicio
@RequiredArgsConstructor /*Genera automaticamente un constructor
 que acepta todos los campos marcados como final (product Repository en nuestro caso) e inyecta las dependencias
 de esos campos*/
@Slf4j //Genera automaticamente un logger (llamado log) que podemos utilizar dentro de nuestra clase
public class ProductService {
    private final ProductRepository productRepository;

    public void addProduct(ProductRequest productRequest){
        var product = Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .status(productRequest.getStatus())
                .build();
        productRepository.save(product);
        log.info("Product added: {}", product);
    }

    /*.stream() convierte una coleccion de elementos en un stream, que puede ser procesado eficientemente y en paralelo
    En este contexto se usa para tener acceso al metodo .map(), que recibe una referencia a una funcion como parametro
    Tal referencia se la proporcionamos usando la sintaxis de this::mapToProductResponse
    Esta sintaxis retorna una referencia a nuestra funcion mapToProductResponse, por esta razon es que no
    necesitamos pasar el products como parametro, ya que por cada Product en la lista, se ejecutara la funcion
    que pasamos como referencia, en este caso mapToProductResponse*/
    public List<ProductResponse> getAllProducts(){
        var products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .status(product.getStatus())
                .build();
    }
}
