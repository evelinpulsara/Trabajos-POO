package com.pedidosiker.demo.service;

import com.pedidosiker.demo.dto.OpcionTreeDTO;
import com.pedidosiker.demo.model.Opcion;
import com.pedidosiker.demo.repository.OpcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OpcionService {

    @Autowired
    private OpcionRepository opcionRepository;

    public List<Opcion> findAll() {
        return opcionRepository.findByActivoTrueOrderByOrdenAscIdAsc();
    }

    public List<OpcionTreeDTO> getMenuHierarchy() {
        List<Opcion> allOptions = findAll();
        
        Map<Long, OpcionTreeDTO> optionMap = new HashMap<>();
        List<OpcionTreeDTO> dtoList = new ArrayList<>();
        
        for (Opcion opcion : allOptions) {
            OpcionTreeDTO dto = convertToDTO(opcion);
            optionMap.put(dto.getId(), dto);
            dtoList.add(dto);
        }
        
        List<OpcionTreeDTO> rootOptions = new ArrayList<>();
        
        for (OpcionTreeDTO dto : dtoList) {
            if (dto.getPadreOpcionId() == null) {
                rootOptions.add(dto);
            } else {
                OpcionTreeDTO padre = optionMap.get(dto.getPadreOpcionId());
                if (padre != null) {
                    padre.addHijo(dto);
                }
            }
        }
        
        return rootOptions;
    }

    public Optional<Opcion> findById(Long id) {
        return opcionRepository.findById(id);
    }

    public Opcion save(Opcion opcion) {
        return opcionRepository.save(opcion);
    }

    public void deleteById(Long id) {
        opcionRepository.deleteById(id);
    }

    private OpcionTreeDTO convertToDTO(Opcion opcion) {
        return new OpcionTreeDTO(
            opcion.getId(),
            opcion.getNombre(),
            opcion.getPadreOpcionId(),
            opcion.getRuta(),
            opcion.getIcono(),
            opcion.getOrden()
        );
    }
}
