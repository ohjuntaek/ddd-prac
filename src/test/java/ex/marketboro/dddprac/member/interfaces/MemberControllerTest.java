package ex.marketboro.dddprac.member.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import ex.marketboro.dddprac.common.RestDocsConfiguartion;
import ex.marketboro.dddprac.member.application.MemberService;
import ex.marketboro.dddprac.member.dto.GoodsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-sources/snippets")
@Import(RestDocsConfiguartion.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    GoodsDTO buyerGoods;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        buyerGoods = new GoodsDTO("gcode", "gname", "gcategory");
    }

    @Test
    @DisplayName("????????? ????????? ????????????")
    void createOwnGoods() throws Exception {
        given(memberService.createGoodsOfMember(any(), any())).willReturn(buyerGoods);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/goods?member_login_id=buyer")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content("{\n" +
                                        "  \"code\": \"code\",\n" +
                                        "  \"name\": \"gname\",\n" +
                                        "  \"category\": \"gcategory\"\n" +
                                        "}")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").exists())
                .andDo(
                        document("create-own-good",
                                requestParameters(
                                        parameterWithName("member_login_id").description("????????? ????????? ?????????")
                                ),
                                requestHeaders(
                                        headerWithName(HttpHeaders.ACCEPT).description("accept heaader"),
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                                ),
                                requestFields(
                                        fieldWithPath("code").description("???????????? ????????? ??????"),
                                        fieldWithPath("name").description("???????????? ????????? ??????"),
                                        fieldWithPath("category").description("???????????? ????????? ??????")
                                )
                                ,
                                relaxedResponseFields(
                                        fieldWithPath("code").description("???????????? ????????? ??????"),
                                        fieldWithPath("name").description("???????????? ????????? ??????"),
                                        fieldWithPath("category").description("???????????? ????????? ??????")
                                )
                        )
                )

        ;
    }

    @Test
    @DisplayName("????????? ????????? ????????? ???????????? ????????????.")
    void getGoodsOfMemberByCode() throws Exception {
        given(memberService.getGoodsByCode("buyer", "gcode")).willReturn(buyerGoods);

        mockMvc
                .perform(RestDocumentationRequestBuilders.get("/goods/{code}?member_login_id=buyer", "gcode"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("gname")))
                .andDo(document("goods",
                        pathParameters(
                                parameterWithName("code").description("????????? ?????? ??????")
                        ),
                        requestParameters(
                                parameterWithName("member_login_id").description("????????? ????????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("????????? ????????? ??????"),
                                fieldWithPath("name").description("????????? ????????? ??????"),
                                fieldWithPath("category").description("????????? ????????? ??????")
                        )
                ));
    }

    @Test
    void getGoodOfOtherMember() {
    }

    @Test
    void updateGoods() {
    }

    @Test
    void deleteGoods() {
    }
}