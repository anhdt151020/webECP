package com.edu.model;

import com.edu.entity.Menu;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class MenuModel {
    private Menu recentMenu;
    private List<MenuModel> child = new ArrayList<>();
    private String idMenu;

}
