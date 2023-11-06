package dev.aarow.auction.utility.other;

import dev.aarow.auction.adapters.PaginatedMenu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuUtility {

    public static int IndexFromPaginatedSlot(int paginatedSlot){
        List<Integer> paginatedSlots = Arrays.stream(PaginatedMenu.paginatedSlots).boxed().toList();

        return paginatedSlots.indexOf(paginatedSlot);
    }
}
