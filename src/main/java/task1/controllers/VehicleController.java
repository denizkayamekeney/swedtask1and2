package task1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import task1.dto.PagerModel;
import task1.dto.Vehicle;
import task1.services.VehiclesService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class VehicleController {

    private static final int BUTTONS_TO_SHOW = 11;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 15;
    private static final int[] PAGE_SIZES = { 15, 25, 50, 100};

    @Autowired
    VehiclesService vehiclesService;

    @GetMapping("/vehicles/findall")
    public ModelAndView findAll(@RequestParam("pageSize") Optional<Integer> pageSize,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("sortField") Optional<String> sortField,
                                @RequestParam("sortDir") Optional<String> sortDir
                                ){

        ModelAndView modelAndView = new ModelAndView("index");

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        String evalSortField = sortField.orElse("id");
        String evalSortDirection = sortDir.orElse("asc");


        Sort sort = evalSortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(evalSortField).ascending() :
                Sort.by(evalSortField).descending();

        Pageable paging = PageRequest.of(evalPage, evalPageSize, sort);
        Page<Vehicle> vehicles = vehiclesService.findAll(paging);

        PagerModel pager = new PagerModel(vehicles.getTotalPages(),vehicles.getNumber(),BUTTONS_TO_SHOW);
        modelAndView.addObject("vehicles",vehicles);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("sortField", sortField.orElse("id"));
        modelAndView.addObject("sortDir", evalSortDirection);
        modelAndView.addObject("reverseSortDir", evalSortDirection.equals("asc") ? "desc" : "asc");

        return modelAndView;
    }

    @GetMapping("/vehicles/updateForm/")
    public String showFormForUpdate(@RequestParam("id") int id,
                                    Model model) {
        Vehicle vehicle = vehiclesService.findById(id).orElse(null);
        model.addAttribute("vehicle", vehicle);
        return "vehicle-form";
    }
    @GetMapping("/vehicles/add/")
    public String showFormForAdd(Model model) {
        Vehicle vehicle = new Vehicle();
        model.addAttribute("vehicle", vehicle);
        return "vehicle-form";
    }
    @PostMapping ("/vehicles/save/")
    public String saveVehicle( @Valid @ModelAttribute("vehicle") Vehicle vehicle, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "vehicle-form";
        }
        vehiclesService.save(vehicle);
        return "redirect:/vehicles/findall/";
    }

    @GetMapping("/vehicles/delete/")
    public String delete(@RequestParam("id") int id) {
        vehiclesService.deleteById(id);
        return "redirect:/vehicles/findall/";

    }

}
