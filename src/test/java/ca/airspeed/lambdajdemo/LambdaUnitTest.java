/**
 * Copyright 2015 Airspeed Consulting
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.package ca.airspeed.lambdajdemo;
 */
package ca.airspeed.lambdajdemo;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static ch.lambdaj.Lambda.sort;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LambdaUnitTest {

    private static final String EMPLOYEE = "Employee";
    private static final String CONTRACTOR = "Contractor";

    private List<Person> people;

    @Before
    public void setUp() throws Exception {
        people = staff();
    }

    @Test
    public void testSelectUsingLambdaj() {
        List<Person> hiredGuns = select(people,
                having(on(Person.class).getRelationship(), equalTo(CONTRACTOR)));

        assertThat(hiredGuns, hasSize(equalTo(1)));
    }

    @Test
    public void testSelect() throws Exception {
        List<Person> hiredGuns = new ArrayList<Person>();
        for (Person person : people) {
            String relationship = person.getRelationship();
            if (relationship != null && relationship.equals(CONTRACTOR)) {
                hiredGuns.add(person);
            }
        }
        assertThat(hiredGuns, hasSize(equalTo(1)));
    }

    @Test
    public void testSortByLastNameUsingLambdaj() throws Exception {
        List<Person> sorted = sort(people, on(Person.class).getLastName());

        assertThat(sorted.get(0).getLastName(), equalTo("Flintstone"));
        assertThat(sorted.get(1).getLastName(), equalTo("Red"));
        assertThat(sorted.get(2).getLastName(), equalTo("Schalme"));
    }

    @Test
    public void testSortByLastName() throws Exception {
        Collections.sort(people, new Comparator<Person>() {
            public int compare(Person thisOne, Person thatOne) {
                return thisOne.getLastName().compareTo(thatOne.getLastName());
            }
        });

        assertThat(people.get(0).getLastName(), equalTo("Flintstone"));
        assertThat(people.get(1).getLastName(), equalTo("Red"));
        assertThat(people.get(2).getLastName(), equalTo("Schalme"));
    }

    private List<Person> staff() {
        Department sd = new Department();
        sd.setId(417);
        sd.setName("Software Development");
        Department san = new Department();
        san.setId(418);
        san.setName("Systems Analysis");

        Person fred = new Person();
        fred.setId(0);
        fred.setFirstName("Fred");
        fred.setLastName("Flintstone");
        fred.setRelationship(EMPLOYEE);
        fred.setDepartment(sd);
        Person brian = new Person();
        brian.setId(9567);
        brian.setFirstName("Brian");
        brian.setLastName("Schalme");
        brian.setRelationship(CONTRACTOR);
        brian.setDepartment(sd);
        Person jen = new Person();
        jen.setId(99);
        jen.setFirstName("Jennifer");
        jen.setLastName("Red");
        jen.setRelationship(EMPLOYEE);
        jen.setDepartment(san);

        return asList(new Person[] { fred, brian, jen });
    }
}
