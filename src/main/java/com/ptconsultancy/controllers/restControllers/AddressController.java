package com.ptconsultancy.controllers.restControllers;

import com.ptconsultancy.admin.adminsupport.ControllerConstants;
import com.ptconsultancy.admin.security.SecurityTokenManager;
import com.ptconsultancy.domain.Address;
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

    private SecurityTokenManager securityTokenManager;

    @Autowired
    public AddressController(AddressEntityRepository addressEntityRepository, PropertiesHandler propertiesHandler,
        SecurityTokenManager securityTokenManager) {
        this.addressEntityRepository = addressEntityRepository;
        this.propertiesHandler = propertiesHandler;
        this.securityTokenManager = securityTokenManager;
    }

    @RequestMapping(path="/getPrimaryAddress/{addressId}/{token}", method=RequestMethod.GET)
    public AddressEntity getAddress(@PathVariable String addressId, @PathVariable String token) {

        AddressEntity output = null;

        if (token.equals(securityTokenManager.getValueWithReset())) {
            for (AddressEntity entity : addressEntityRepository.findByUserId(Long.parseLong(addressId))) {
                if (entity.isPrimaryAddress()) {
                    output = entity;
                    break;
                }
            }
        }

        return output;
    }

    @RequestMapping(path="/getAllUserAddresses/{addressId}/{token}", method=RequestMethod.GET)
    public List<AddressEntity> getAllAddressesForUser(@PathVariable String addressId, @PathVariable String token) {

        if (token.equals(securityTokenManager.getValueWithReset())) {
            return addressEntityRepository.findByUserId(Long.parseLong(addressId));
        } else {
            return null;
        }
    }


    @RequestMapping(path="/savePrimaryAddress/{userId}/{password}/{token}", method=RequestMethod.POST)
    public void setPrimaryAddress(@RequestBody() Address address, @PathVariable String userId, @PathVariable String password, @PathVariable String token) {

        if (token.equals(securityTokenManager.getValueWithReset())) {
            boolean primaryAddress = false;
            for (AddressEntity entity : addressEntityRepository.findByUserId(address.getUserId())) {
                primaryAddress = entity.isPrimaryAddress();
                if (primaryAddress) {
                    break;
                }
            }

            if (!primaryAddress) {
                AddressEntity addressEntity = new AddressEntity(address.getUserId(), address.getHouseNameNumber(), address.getAddressLine1(), address.getAddressLine2(),
                    address.getAddressLine3(), address.getAddressLine4(), address.getCounty(), address.getCountry(), address.getPostCode(), true);

                if (userId.equals(propertiesHandler.getProperty(ControllerConstants.ID_KEY)) && password.equals(propertiesHandler.getProperty(ControllerConstants.PASS_KEY))) {
                    addressEntityRepository.save(addressEntity);
                }
            }
        }
    }

    @RequestMapping(path="/saveSecondaryAddress/{userId}/{password}/{token}", method=RequestMethod.POST)
    public void setSecondaryAddress(@RequestBody() Address address, @PathVariable String userId, @PathVariable String password, @PathVariable String token) {

        if (token.equals(securityTokenManager.getValueWithReset())) {
            AddressEntity addressEntity = new AddressEntity(address.getUserId(), address.getHouseNameNumber(), address.getAddressLine1(), address.getAddressLine2(),
                address.getAddressLine3(), address.getAddressLine4(), address.getCounty(), address.getCountry(), address.getPostCode(), false);

            if (userId.equals(propertiesHandler.getProperty(ControllerConstants.ID_KEY)) && password.equals(propertiesHandler.getProperty(ControllerConstants.PASS_KEY))) {
                addressEntityRepository.save(addressEntity);
            }
        }
    }

    @RequestMapping(path="/deleteAddress/{userId}/{password}/{addressId}/{token}", method=RequestMethod.DELETE)
    public void deleteAddress(@PathVariable String userId, @PathVariable String password, @PathVariable String addressId, @PathVariable String token) {

        if (token.equals(securityTokenManager.getValueWithReset())) {
            if (userId.equals(propertiesHandler.getProperty(ControllerConstants.ID_KEY)) && password.equals(propertiesHandler.getProperty(ControllerConstants.PASS_KEY))) {
                List<AddressEntity> entities = addressEntityRepository.findByUserId(Long.parseLong(addressId));
                if (entities.size() == 1) {
                    addressEntityRepository.delete(entities.get(0));
                }
            }
        }
    }

    @RequestMapping(path="/address/getadminid/{token}", method=RequestMethod.GET)
    public String getAdminId(@PathVariable String token) {
        if (token.equals(securityTokenManager.getValueWithReset())) {
            return propertiesHandler.getProperty(ControllerConstants.ID_KEY);
        } else {
            return ControllerConstants.NO_TOKEN_MESSAGE;
        }
    }

    @RequestMapping(path="/address/getadminpass/{token}", method=RequestMethod.GET)
    public String getAdminPass(@PathVariable String token) {
        if (token.equals(securityTokenManager.getValueWithReset())) {
            return  propertiesHandler.getProperty(ControllerConstants.PASS_KEY);
        } else {
            return ControllerConstants.NO_TOKEN_MESSAGE;
        }
    }
}
