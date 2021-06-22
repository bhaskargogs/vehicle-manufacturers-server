/*
 * Copyright 2021 Bhaskar Gogoi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.server.manufacturers;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.server.manufacturers.dto.ManufacturerResponse;
import org.server.manufacturers.entity.Manufacturer;
import org.server.manufacturers.entity.VehicleTypes;
import org.server.manufacturers.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/*
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class VehicleManufacturerIntegrationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Before
    public void setUp() throws Exception {
        VehicleTypes vehicleTypes = new VehicleTypes(true, "Passenger Car");
        this.manufacturerRepository.save(new Manufacturer(250L, "Japan", "Toyota",
                        "Toyota Corporation", 1876L, Collections.singletonList(vehicleTypes)));
    }

    @Test
    public void getManufacturer_returnsManufacturerDetails() throws Exception {
        ManufacturerResponse response = this.webTestClient.get().uri("/manufacturers/{id}", 250L)
                .exchange().expectStatus().isOk()
                .expectBody(ManufacturerResponse.class).returnResult().getResponseBody();

        assertThat(response.getId()).isEqualTo(250L);
        assertThat(response.getCountry()).isEqualTo("Japan");

    }
}
*/
