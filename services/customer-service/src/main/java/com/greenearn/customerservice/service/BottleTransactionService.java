package com.greenearn.customerservice.service;


import com.greenearn.customerservice.dto.BottleTransactionRequestDto;
import com.greenearn.customerservice.dto.BottleTransactionResponseDto;
import com.greenearn.customerservice.dto.PublicCustomerResponseDto;
import com.greenearn.customerservice.dto.projection.DailyPointProjectionDto;
import com.greenearn.customerservice.dto.projection.TopCustomerDto;
import com.greenearn.customerservice.entity.BottleTransactionEntity;
import com.greenearn.customerservice.entity.CustomerEntity;
import com.greenearn.customerservice.enums.BottleTransactionStatus;
import com.greenearn.customerservice.mapper.BottleTransactionMapper;
import com.greenearn.customerservice.mapper.CustomerMapper;
import com.greenearn.customerservice.repository.BottleTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class BottleTransactionService {

    private final BottleTransactionRepository bottleTransactionRepository;
    private final CustomerService customerService;
    private final CurrentCustomerService currentCustomerService;
    private final BottleTransactionMapper bottleTransactionMapper;
    private final CustomerMapper customerMapper;


    public void createBottleTransaction(BottleTransactionRequestDto bottleTransactionRequestDto, Authentication authentication) {
        BottleTransactionEntity bottleTransactionEntity = bottleTransactionMapper.mapTransactionRequestDtoToEntity(bottleTransactionRequestDto, authentication);
        try {
            bottleTransactionEntity.setBottleTransactionStatus(BottleTransactionStatus.SUCCESS);
            bottleTransactionRepository.save(bottleTransactionEntity);
            customerService.updateCustomerPointsOnBottleTransaction(bottleTransactionRequestDto, authentication);
        } catch (Exception e) {
            log.error(e.getMessage());
            bottleTransactionEntity.setBottleTransactionStatus(BottleTransactionStatus.FAILED);
            bottleTransactionEntity.setEarnedPoints(0);
            bottleTransactionRepository.save(bottleTransactionEntity);
        }
    }

    public List<BottleTransactionResponseDto> getAllBottleTransactions() {
        try {
            return bottleTransactionRepository.findAll()
                    .stream()
                    .sorted(Comparator.comparing(BottleTransactionEntity::getCreatedAt).reversed())
                    .map(bottleTransactionEntity -> bottleTransactionMapper.map2ResponseDto(bottleTransactionEntity))
                    .toList();
        } catch (Exception e) {
            log.error("Error while fetching all bottle transactions", e);
            return new ArrayList<>();
        }
    }

    public BottleTransactionResponseDto getBottleTransactionById(UUID id) {
        return bottleTransactionMapper.map2ResponseDto(
                bottleTransactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Bottle transaction not found with id: " + id))
        );
    }

    public List<BottleTransactionResponseDto> getAllBottleTransactionsByCustomerId(UUID customerId) {
        try {
            return bottleTransactionRepository.findBottleTransactionEntitiesByCustomerId(customerId)
                    .stream()
                    .sorted(Comparator.comparing(BottleTransactionEntity::getCreatedAt).reversed())
                    .map(bottleTransactionEntity -> bottleTransactionMapper.map2ResponseDto(bottleTransactionEntity))
                    .toList();
        } catch (Exception e) {
            log.error("Error while fetching all bottle transactions by customer id", e);
            return new ArrayList<>();
        }
    }


    public List<BottleTransactionResponseDto> getMyAllBottleTransactions(Authentication authentication) {
        UUID customerId = currentCustomerService.getCurrentCustomerId(authentication);
        try {
            return bottleTransactionRepository.findBottleTransactionEntitiesByCustomerId(customerId)
                    .stream()
                    .sorted(Comparator.comparing(BottleTransactionEntity::getCreatedAt).reversed())
                    .map(bottleTransactionEntity -> bottleTransactionMapper.map2ResponseDto(bottleTransactionEntity))
                    .toList();
        } catch (Exception e) {
            log.error("Error while fetching all bottle transactions by customer id", e);
            return new ArrayList<>();
        }
    }

    public BottleTransactionResponseDto getMyBottleTransactionById(Authentication authentication, UUID id) {
        UUID customerId = currentCustomerService.getCurrentCustomerId(authentication);
        BottleTransactionEntity bottleTransactionEntity = bottleTransactionRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Bottle transaction not found with id: " + id));
        if (!Objects.equals(customerId, bottleTransactionEntity.getCustomerId())) {
            throw new AccessDeniedException("You do not have permission to access this bottle transaction.");
        }
        return bottleTransactionMapper.map2ResponseDto(bottleTransactionEntity);
    }

    public List<PublicCustomerResponseDto> getTop5CustomersLastMonth() {
        try {
            LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
            List<TopCustomerDto> topCustomers = bottleTransactionRepository.findTop5CustomersSince(startOfMonth);

            return topCustomers.stream()
                    .map(topCustomerDto -> {
                        PublicCustomerResponseDto publicCustomerResponseDto =
                                customerMapper.map2PublicResponseDto(
                                        customerService.getCustomerById(topCustomerDto.getCustomerId())
                                );
                        publicCustomerResponseDto.setTotalPoints(topCustomerDto.getTotalPoints());
                        return publicCustomerResponseDto;
                    })
                    .sorted(Comparator.comparing(PublicCustomerResponseDto::getTotalPoints).reversed())
                    .toList();
        } catch (Exception e) {
            log.error("Error while fetching top 5 customers last month", e);
            throw new RuntimeException("Error while fetching top 5 customers last month", e);
        }
    }

    public Map<LocalDate, Integer> getWeeklyPointsByCustomer(Authentication authentication, String clientTimeZone) {
        UUID customerId = currentCustomerService.getCurrentCustomerId(authentication);

        LocalDate today = LocalDate.now(ZoneId.of(clientTimeZone));
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);

        ZonedDateTime startOfWeekZoned = startOfWeek.atStartOfDay(ZoneId.of(clientTimeZone));
        LocalDateTime startOfWeekUtc = startOfWeekZoned.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        ZonedDateTime startOfTomorrowZoned = today.plusDays(1).atStartOfDay(ZoneId.of(clientTimeZone));
        LocalDateTime endOfTodayUtc = startOfTomorrowZoned.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        List<DailyPointProjectionDto> rawResults = bottleTransactionRepository.findWeeklyPointsByCustomer(
                customerId, startOfWeekUtc, endOfTodayUtc, clientTimeZone);

        Map<LocalDate, Integer> resultMap = rawResults.stream()
                .collect(Collectors.toMap(
                        DailyPointProjectionDto::getDate,
                        DailyPointProjectionDto::getTotalPoints
                ));

        Map<LocalDate, Integer> completeWeekMap = new LinkedHashMap<>();
        long daysBetween = ChronoUnit.DAYS.between(startOfWeek, today);
        for (int i = 0; i <= daysBetween; i++) {
            LocalDate day = startOfWeek.plusDays(i);
            completeWeekMap.put(day, resultMap.getOrDefault(day, 0));
        }

        return completeWeekMap;
    }

}
