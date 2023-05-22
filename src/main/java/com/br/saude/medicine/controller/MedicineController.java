package com.br.saude.medicine.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.saude.medicine.model.Medicine;
import com.br.saude.medicine.service.IServiceMedicine;
import com.br.saude.infrastructure.support.ObjectUtils;
import com.br.saude.infrastructure.support.RequestHeaderHolder;

/*=========================ATENÇÃO=========================
 * As classes a seguir estão organizadas em ordem alfabética assim como seus respectivos métodos. Caso for fazer alguma alteração, continue com a organização!
 *  
*/
@RestController
@RequestMapping(path = "/api/v1/app/medicine")
public class MedicineController {
    // ---------------------------------------------------
    // ------------- MEDICINE -----------------------------
    // ---------------------------------------------------
    @Autowired
    private IServiceMedicine serviceMedicine;

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public boolean deletar(@PathVariable String id, @RequestHeader(value = "UserId", required = false) String userId) {
        RequestHeaderHolder.addToHeaders("UserId", userId);

        Medicine medicine = new Medicine();
        medicine.setId(id);
        serviceMedicine.delete(medicine);
        return true;
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public Medicine salvar(@RequestBody Medicine medicine,
            @RequestHeader(value = "UserId", required = false) String userId) {

        RequestHeaderHolder.addToHeaders("UserId", userId);

        return serviceMedicine.save(medicine);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public Medicine update(@RequestBody Map<String, Object> updates,
            @RequestHeader(value = "UserId", required = false) String userId) {
        updates.put("updatedById", userId);
        updates.put("updatedAt", LocalDateTime.now());

        RequestHeaderHolder.addToHeaders("UserId", userId);

        return ObjectUtils.updateOrSaveObject(updates, serviceMedicine);
    }

    @RequestMapping(path = "/all")
    public List<Medicine> get(@RequestHeader(value = "UserId", required = false) String userId) {
        return serviceMedicine.getAll();
    }

    @RequestMapping(path = "/find/{id}")
    public Medicine getFl(@PathVariable String id, @RequestHeader(value = "UserId", required = false) String userId) {

        return (Medicine) this.serviceMedicine.findById(id);
    }
}