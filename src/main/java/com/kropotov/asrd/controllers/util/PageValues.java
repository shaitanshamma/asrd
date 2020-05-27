package com.kropotov.asrd.controllers.util;

import com.kropotov.asrd.entities.common.InfoEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

public final class PageValues {
	public static final int INITIAL_PAGE_NUMBER = 1;
	public static final int[] PAGE_SIZES = {10, 25, 50, 100};
	public static final Pageable DEFAULT_PAGEABLE = PageRequest.of(INITIAL_PAGE_NUMBER, PAGE_SIZES[0], Sort.by("id"));

	public static Pageable getPageableOrDefault(Pageable pageable) {
		if (pageable == null || pageable.getPageNumber() == 0 && pageable.getPageSize() == 20) {    // default global Pageable is PageRequest.of(0, 20)
			pageable = DEFAULT_PAGEABLE;															// if so we change that for our defaults
		}
		return pageable;
	}

	public static void addContentToModel(Model model, PageWrapper<? extends InfoEntity> page) {
		model.addAttribute("page", page);
		model.addAttribute("pageSizes", PAGE_SIZES);
	}
}
