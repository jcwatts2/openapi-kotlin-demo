package com.test.docs.docsdemo.api

import com.test.docs.docsdemo.api.model.Hello
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloApiController : HelloApi {

    override fun hello(): ResponseEntity<Hello> {
        return ResponseEntity.ok(Hello("First Try"))
    }

}