package com.suddenly.util.page;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @Author: zzw
 */
public class PageUtil {

    /**
     * spring data jpa 分页
     * @param dateList
     * @param pageNo
     * @param pageSize
     */
//    public static <T> Page<T> getPage(List<T> dateList, Integer pageNo, Integer pageSize) {
//        int fromIndex = pageSize * (pageNo - 1);
//        int toIndex = pageSize * pageNo;
//        if (pageNo == 0) {
//            fromIndex = pageSize * pageNo;
//            toIndex = pageSize * (pageNo + 1);
//        }
//
//        if (toIndex > dateList.size()) {
//            toIndex = dateList.size();
//        }
//        if (fromIndex > toIndex) {
//            fromIndex = toIndex;
//        }
//        //获取list的分页集合
//        List<T> result = dateList.subList(fromIndex, toIndex);
//        //创建page对象，并且可以排序
//
//        return new PageImpl<>(result, PageRequest.of(pageNo == 0 ? pageNo : pageNo - 1, pageSize), dateList.size());
//    }

    /**
     * spring data jpa 分页
     * @param page
     * @param pageResDto
     * @param pageNo
     * @param pageSize
     */
//    protected static <T extends BaseDto> PageResDto<T> setPageInfo(Page<T> page,
//                                                                   PageResDto<T> pageResDto,
//                                                                   Integer pageNo,
//                                                                   Integer pageSize) {
//        long total = page.getTotalElements();
//        pageResDto.setDateList(page.getContent());
//        pageResDto.setTotalCount(total);
//        pageResDto.setPageNo(pageNo);
//        pageResDto.setPageSize(pageSize);
//        if (pageSize != 0) {
//            pageResDto.setPageCount(Math.toIntExact(
//                    total % pageSize == 0
//                            ? (total / pageSize)
//                            : (total / pageSize) + 1));
//        } else {
//            pageResDto.setPageCount(0);
//        }
//        return pageResDto;
//    }





    public static <T> List<T> getPageList(List<T> dateList, Integer pageNo, Integer pageSize) {
        int fromIndex = pageSize * (pageNo - 1);
        int toIndex = pageSize * pageNo;
        if (pageNo == 0) {
            fromIndex = pageSize * pageNo;
            toIndex = pageSize * (pageNo + 1);
        }

        if (toIndex > dateList.size()) {
            toIndex = dateList.size();
        }
        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }
        //获取list的分页集合
        return dateList.subList(fromIndex, toIndex);
    }


    protected static <T extends BaseDto> PageResDto<T> setPageInfoList(List<T> list,
                                                                   PageResDto<T> pageResDto,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        long total = list.size();
        pageResDto.setDateList(list);
        pageResDto.setTotalCount(total);
        pageResDto.setPageNo(pageNo);
        pageResDto.setPageSize(pageSize);
        if (pageSize != 0) {
            pageResDto.setPageCount(Math.toIntExact(
                    total % pageSize == 0
                            ? (total / pageSize)
                            : (total / pageSize) + 1));
        } else {
            pageResDto.setPageCount(0);
        }
        return pageResDto;
    }



    /**
     * PageHelper查询结果中分页信息设置
     * @param page ;
     * @param pageResDto ;
     * @param <T> ;
     */
//    protected static <T extends BaseDto> PageResDto<T> setPageInfo(com.github.pagehelper.Page<?> page,
//                                                                   PageResDto<T> pageResDto){
//        pageResDto.setPageCount(page.getPages());
//        pageResDto.setPageSize(page.getPageSize());
//        pageResDto.setPageNo(page.getPageNum());
//        pageResDto.setTotalCount(page.getTotal());
//        return pageResDto;
//    }



}
