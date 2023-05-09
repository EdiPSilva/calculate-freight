package br.com.java.calculatefreight.application.orders.resources;

import br.com.java.calculatefreight.application.orders.OrdersService;
import br.com.java.calculatefreight.utils.DefaultLog;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdersController {

    @Autowired
    private DefaultLog defaultLog;

    @Autowired
    private OrdersService ordersService;

    private static final String API_V1 = "/orders/v1/";

    @ApiOperation("Cadastro pedido")
    @PostMapping(value = "/v1/")
    public ResponseEntity<OrdersResponse> create(@Valid @RequestBody OrdersRequest ordersRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.POST, API_V1);
        return new ResponseEntity<>(ordersService.create(ordersRequest), HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza pedido")
    @PutMapping(value = "/v1/")
    public ResponseEntity<OrdersResponse> update(@Valid @RequestBody OrdersRequest ordersRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.PUT, API_V1);
        return new ResponseEntity<>(ordersService.update(ordersRequest), HttpStatus.CREATED);
    }
}
