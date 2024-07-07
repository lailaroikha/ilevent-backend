package com.ilevent.ilevent_backend.price.service.impl;

import com.ilevent.ilevent_backend.price.dto.PriceRequestDto;
import com.ilevent.ilevent_backend.price.dto.PriceResponseDto;
import com.ilevent.ilevent_backend.price.repository.PriceRepository;
import com.ilevent.ilevent_backend.price.service.PriceService;
import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import com.ilevent.ilevent_backend.price.entity.Price;
import com.ilevent.ilevent_backend.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final TicketRepository ticketRepository;

    public PriceServiceImpl(PriceRepository priceRepository, TicketRepository ticketRepository) {
        this.priceRepository = priceRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public PriceResponseDto createPrice(PriceRequestDto dto) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(dto.getTypeticketId());
        if (ticketOptional.isEmpty()) {
            throw new RuntimeException("Ticket not found");
        }
        Price price = new Price();
        price.setTypeticketId(ticketOptional.get());
        price.setPriceBeforeDiscount(dto.getPriceBeforeDiscount());
        price.setTotalDiscount(dto.getTotalDiscount());
        price.setCreatedAt(Instant.now());
        price.setUpdateAt(Instant.now());

        price = priceRepository.save(price);

        return PriceResponseDto.fromEntity(price);
    }

    @Override
    public PriceResponseDto getPriceById(Long id) {
        Price price = priceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Price not found"));
        return PriceResponseDto.fromEntity(price);
    }

    @Override
    public void deletePrice(Long id) {
        priceRepository.deleteById(id);
    }

    @Override
    public Double calculatePriceAfterDiscount(Double priceBeforeDiscount, Double totalDiscount) {
        if (priceBeforeDiscount == null || totalDiscount == null) {
            throw new IllegalArgumentException("Price before discount and total discount must not be null");
        }

        // Calculate the price after discount
        Double discountMultiplier = 1 - (totalDiscount / 100.0);
        return (double) Math.round(priceBeforeDiscount * discountMultiplier);
    }
}
