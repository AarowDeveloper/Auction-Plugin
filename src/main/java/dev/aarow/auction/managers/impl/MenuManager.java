package dev.aarow.auction.managers.impl;

import dev.aarow.auction.adapters.Menu;
import dev.aarow.auction.adapters.PaginatedMenu;
import dev.aarow.auction.managers.Manager;
import lombok.Getter;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class MenuManager extends Manager {

    private Map<UUID, Menu> menuCache = new HashMap<>();
    private Map<UUID, PaginatedMenu> paginatedMenuCache = new HashMap<>();


    @Override
    public void setup() {}
}
