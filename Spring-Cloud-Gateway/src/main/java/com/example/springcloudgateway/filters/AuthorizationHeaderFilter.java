package com.example.springcloudgateway.filters;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    /*Note : this is a type of Custom Filter
        i.e. it only applies to the routes which we devs configure via
        application.properties file.

        But Global Filters applies to all the available routes in whole application,
        so be aware of that...
    */

    @Autowired
    private Environment environment;

    public AuthorizationHeaderFilter(){
        super(Config.class);
    }

    public static class Config{
        //Put configuration properties here
    }

    @Override
    public GatewayFilter apply(Config config) {
        //Main custom logic of our filter will be here
        return (exchange, chain) -> {

            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            if(!serverHttpRequest.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);

            }

            String authorizationHeader = serverHttpRequest.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if(!isJwtValid(jwt)){
                return onError(exchange, "Jwt Token isn't valid ", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        };
    }


    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus){
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.setStatusCode(httpStatus);
        return serverHttpResponse.setComplete();
    }

    private boolean isJwtValid(String jwtToken){
        String subject = null;
        try{
            subject = Jwts.parser().setSigningKey(environment.getProperty("token.secret")).
                    parseClaimsJws(jwtToken).getBody().
                    getSubject();
        }catch(Exception e){
            return false;
        }


        if(subject == null || subject.equals("")) return false;
        return true;
    }
}