package solux.woodong.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import solux.woodong.web.domain.club.Club;
import solux.woodong.web.domain.club.ClubRepository;
import solux.woodong.web.domain.receipt.Receipt;
import solux.woodong.web.domain.receipt.ReceiptRepository;
import solux.woodong.web.dto.receipt.ReceiptResponseDto;
import solux.woodong.web.dto.receipt.ReceiptSaveRequestDto;
import solux.woodong.web.dto.receipt.ReceiptUpdateRequestDto;
import solux.woodong.web.service.receipt.ReceiptService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReceiptApiController {
    private final ReceiptService receiptService;

    private final ReceiptRepository receiptRepository;

    private final ClubRepository clubRepository;

    @PostMapping("/api/udong/receipt/{club_id}")
    public Long save(@PathVariable Long club_id, @RequestBody ReceiptSaveRequestDto requestDto) {
        Club clubReceipt = clubRepository.findById(club_id).orElseThrow(
                ()->new IllegalArgumentException("오류"));
        requestDto = ReceiptSaveRequestDto.builder()
                .cost(requestDto.getCost())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .picture(requestDto.getPicture())
                .useDate(requestDto.getUseDate())
                .club(clubReceipt).build();
        return receiptService.save(requestDto);
    }

    @PutMapping("/api/udong/receipt/{id}")
    public Long update(@PathVariable Long id, @RequestBody ReceiptUpdateRequestDto requestDto) {
        return receiptService.update(id, requestDto);
    }

    @GetMapping("/api/udong/receipt/{id}")
    public ReceiptResponseDto findById (@PathVariable Long id) {
        return receiptService.findById(id);
    }

    @GetMapping("/api/udong/receipt")
    public List<Receipt> getReceiptList() {
        return receiptService.findAll();
    }

    @DeleteMapping("/api/udong/receipt/{id}")
    public void deleteReceipt(@PathVariable Long id) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 receipt 없음"+id));
        receipt = Receipt.builder()
                .cost(receipt.getCost())
                .title(receipt.getTitle())
                .content(receipt.getContent())
                .picture(receipt.getPicture())
                .useDate(receipt.getUseDate())
                .club(null).build();
        receiptService.delete(id);
    }
}