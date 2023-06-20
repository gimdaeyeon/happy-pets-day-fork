package com.example.happypetsday.controller.stroll;

import com.example.happypetsday.dto.PetDto;
import com.example.happypetsday.dto.StrollBoardDto;
import com.example.happypetsday.service.pet.PetService;
import com.example.happypetsday.service.stroll.StrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/stroll/*")
@RequiredArgsConstructor
public class StrollController {
    private final PetService petService;
    private final StrollService strollService;


    @GetMapping("/view")
    public String boardView(Long strollBoardNumber,Model model,HttpServletRequest req){
//        Long userNumber = (Long)req.getSession().getAttribute("userNumber");
//        model.addAttribute("pet",petService.findPet(userNumber));
        model.addAttribute("board",strollService.findBoard(strollBoardNumber));
        return "strollBoard/strollBoardView";
    }

//  산책 게시글 작성 화면 이동
    @GetMapping("/write")
    public String boardWrite(HttpServletRequest req, Model model){
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");

        List<PetDto> petList = petService.findPet(userNumber);

//      산책할 수 있는 반려동물이 없으면 등록페이지로 이동시키기
        if(petList.size()<1){
            return "myPage/addPet";
        }
        model.addAttribute("petList", petList);
        return "strollBoard/strollBoardWrite";
    }

//    산책 게시글 작성
    @PostMapping("/write")
    public RedirectView boardWrite(StrollBoardDto strollBoardDto,HttpServletRequest req){
        strollBoardDto.setUserNumber((Long)req.getSession().getAttribute("userNumber"));
        strollService.register(strollBoardDto);

        return new RedirectView("/stroll/list");
    }


    @GetMapping("/list")
    public String strollBoardList(HttpServletRequest req){
        return "strollBoard/strollBoardList";
    }
}
