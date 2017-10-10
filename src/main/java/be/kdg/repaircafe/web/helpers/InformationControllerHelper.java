package be.kdg.repaircafe.web.helpers;

import be.kdg.repaircafe.services.api.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InformationControllerHelper {
    private final InformationService informationService;

    @Autowired
    public InformationControllerHelper(InformationService informationService) {
        this.informationService = informationService;
    }

    public List<SelectOption> getCategoryOptions() {
        return buildOptionList(informationService.getAllCategories());
    }

    public List<SelectOption> getBrandOptions() {
        return buildOptionList(informationService.getAllBrands());
    }

    public List<SelectOption> getDefectOptions() {
        return buildOptionList(informationService.getAllDefects());
    }

    private List<SelectOption> buildOptionList(List<String> options) {
        List<SelectOption> selectOptions = new ArrayList<>();
        for (int i = 1; i < options.size() + 1; i++) {
            selectOptions.add(new SelectOption(String.valueOf(i), options.get(i - 1)));
        }
        return selectOptions;
    }

    class SelectOption {
        private String id;
        private String name;

        public SelectOption(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
