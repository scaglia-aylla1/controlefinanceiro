package com.scaglia.controle_financeiro.service;

import com.scaglia.controle_financeiro.dto.ExpenseDTO;
import com.scaglia.controle_financeiro.entity.CategoryEntity;
import com.scaglia.controle_financeiro.entity.ExpenseEntity;
import com.scaglia.controle_financeiro.entity.ProfileEntity;
import com.scaglia.controle_financeiro.repository.CategoryRepository;
import com.scaglia.controle_financeiro.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    //Adiciona uma nova despesa ao banco de dado
    public ExpenseDTO addExpense(ExpenseDTO dto){
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Categoria não encontrada"));
        ExpenseEntity newExpense = toEntity(dto, profile, category);
        newExpense = expenseRepository.save(newExpense);
        return toDTO(newExpense);
    }

    //Recupera todas as despesas para o mês/base atual com base na data de início e término
    public List<ExpenseDTO> getCurrentMonthExpensesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
        return list.stream().map(this::toDTO).toList();
    }

    //Exclua despesa por ID para o usuário atual
    public void deleteExpense (Long expenseId){
        ProfileEntity profile = profileService.getCurrentProfile();
        ExpenseEntity entity = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        if (!entity.getProfile().getId().equals(profile.getId())){
            throw new RuntimeException("Não autorizado para excluir esta despesa");

        }
        expenseRepository.delete(entity);
    }
    //Obtenha as 5 últimas despesas para o usuário atual
    public List<ExpenseDTO>getLatest5ExpensesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(this::toDTO).toList();
    }
    //Obtenha despesas totais para o usuário atual
    public BigDecimal getTotalExpenseForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal total = expenseRepository.findTotalExpenseByProfileId(profile.getId());
        return total != null ? total: BigDecimal.ZERO;
    }
    //Filtrar Despesas
    public List<ExpenseDTO> filterExpenses(LocalDate startDate, LocalDate endDate, String keyword, Sort sort){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate, endDate, keyword, sort);
        return  list.stream().map(this::toDTO).toList();
    }

    //Notificações
    public List<ExpenseDTO> getExpensesForUserOnDate(Long profileId, LocalDate date){
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDate(profileId, date);
        return  list.stream().map(this::toDTO).toList();
    }

    //Metodos auxiliares
    private ExpenseEntity toEntity(ExpenseDTO dto, ProfileEntity profile, CategoryEntity category){
        return  ExpenseEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profile)
                .category(category)
                .build();
    }
    private ExpenseDTO toDTO(ExpenseEntity entity){
        return ExpenseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId(): null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName(): "N/A")
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

    }


}