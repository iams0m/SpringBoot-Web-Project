package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    // 등록 작업 테스트
    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    // 조회 작업 테스트
    @Test
    public void testSelect() {
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("================================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno);

        System.out.println("================================");

        System.out.println(memo);
    }

    // 수정 작업 테스트
    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
    }

    // 삭제 작업 테스트
    @Test
    public void testDelete() {
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    // 페이징 처리 테스트
    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);

        System.out.println("----------------------------------------------");

        System.out.println("Total Pages: " + result.getTotalPages()); // 총 페이지 수
        System.out.println("Total Count: " + result.getTotalElements()); // 전체 개수
        System.out.println("Page Number: " + result.getNumber()); // 현재 페이지 번호
        System.out.println("Page Size: " + result.getSize()); // 페이지당 데이터 개수
        System.out.println("has next page?: " + result.hasNext()); // 다음 페이지 존재 여부
        System.out.println("first page?: " + result.isFirst());// 시작 페이지(0) 여부

        System.out.println("----------------------------------------------");

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    // 정렬 처리 테스트
    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }
}
