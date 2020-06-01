package com.kropotov.asrd.controllers;

import com.kropotov.asrd.dto.docs.ActInputControlDto;
import com.kropotov.asrd.facades.docs.ActInputControlFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("acts")
@RequiredArgsConstructor
public class ActInputControlController {
    private final ActInputControlFacade actFacade;

    @GetMapping("/{id}/show")
    public String displayById(@PathVariable Long id, Model model) {
        model.addAttribute("act", actFacade.getDtoById(id));
        return "acts/show";
    }

    @GetMapping("/{id}/update")
    public String updateAct(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("act", actFacade.getDtoById(id));
        return "acts/edit-act";
    }

    @GetMapping
    public String displayAll(Model model, Pageable pageable) {
        actFacade.fillPage(model, pageable);
        return "acts/list-acts";
    }

    // todo добавить проверку с какой страницы пришел, НЕЛЬЗЯ ДОПУСТИТЬ ЦИКЛИЧНОГО УДАЛЕНИЯ ВСЕХ ФАЙЛОВ ПО ID!!!!!!!
    // todo Перенести удаление файлов в FileController. Уточнить у преподавателя
    @GetMapping("/{actId}/file/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteActFileById(@PathVariable("actId") Long actId) {
        actFacade.deleteFile(actId);
    }

    @PostMapping("/edit")
    public String saveOrUpdate(@ModelAttribute ActInputControlDto actDto, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        actFacade.save(actDto, principal.getName());
        return "redirect:/acts";
    }
}
