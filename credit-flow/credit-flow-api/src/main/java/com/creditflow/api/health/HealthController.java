package com.creditflow.api.health;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
@RestController//bu sınıfın HTTP isteklerini karşılayacağını Spring e söyler.
@RequestMapping("/api/health")//temel adres belirler

public class HealthController {
    @GetMapping//HTTP GET isteklerinde bu metodun çalışacağını belirtir.
    public Map<String,String> check(){ //check() istek geldiğinde Spring in çağıracağı metottur.diğerleri java objecti.
            return Map.of(//spring dönen map objectini otomatik olarak aşağıdaki JSON a çevirecek.
        "status",
            "CreditFlow API is running"
    );//spring htpp arası akışı yaptık.

    }

}
