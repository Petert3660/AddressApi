package com.ptconsultancy.controllers.restControllers;

import com.ptconsultancy.datamodels.Address;
import com.ptconsultancy.entities.AddressEntity;
import com.ptconsultancy.repositories.AddressEntityRepository;
import com.ptconsultancy.utilities.PropertiesHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    private AddressEntityRepository addressEntityRepository;

    private PropertiesHandler propertiesHandler;

    @Autowired
    public AddressController(AddressEntityRepository addressEntityRepository, PropertiesHandler propertiesHandler) {
        this.addressEntityRepository = addressEntityRepository;
        this.propertiesHandler = propertiesHandler;
    }

    @RequestMapping(path="/getAddress/{addressId}", method=RequestMethod.GET)
    public AddressEntity getAddress(@PathVariable String addressId) {

        return addressEntityRepository.findByUserId(Long.parseLong(addressId)).get(0);
    }

    @RequestMapping(path="/saveAddress/{userId}/{password}", method=RequestMethod.POST)
    public void setAddress(@RequestBody() Address address, @PathVariable String userId, @PathVariable String password) {

        AddressEntity addressEntity = new AddressEntity(address.getUserId(), address.getHouseNameNumber(), address.getAddressLine1(), address.getAddressLine2(),
            address.getAddressLine3(), address.getAddressLine4(), address.getCounty(), address.getCountry(), address.getPostCode());

        if (userId.equals(propertiesHandler.getProperty("auth.addressapi.userid")) && password.equals(propertiesHandler.getProperty("auth.addressapi.password"))) {
            addressEntityRepository.save(addressEntity);
        }
    }

    @RequestMapping(path="/deleteAddress/{userId}/{password}/{addressId}", method=RequestMethod.DELETE)
    public void deleteAddress(@PathVariable String userId, @PathVariable String password, @PathVariable String addressId) {

        if (userId.equals(propertiesHandler.getProperty("auth.addressapi.userid")) && password.equals(propertiesHandler.getProperty("auth.addressapi.password"))) {
            List<AddressEntity> entities = addressEntityRepository.findByUserId(Long.parseLong(addressId));
            if (entities.size() == 1) {
                addressEntityRepository.delete(entities.get(0));
            }
        }
    }
}
