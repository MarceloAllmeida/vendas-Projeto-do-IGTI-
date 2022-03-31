package com.marcelo.vendas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.marcelo.vendas.DTO.ComprasDTO;
import com.marcelo.vendas.DTO.RankingComprasDTO;
import com.marcelo.vendas.entities.Cliente;
import com.marcelo.vendas.entities.Compras;
import com.marcelo.vendas.repositories.ClienteRepository;
import com.marcelo.vendas.repositories.ComprasRepository;

@Service
public class ComprasService {
	@Autowired
	private ComprasRepository comprasRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<ComprasDTO> findAll() {
		clienteRepository.findAll();
		List<Compras>res=comprasRepository.findAll();
		return res.stream().map(c->new ComprasDTO(c)).collect(Collectors.toList());
	}

	public List<RankingComprasDTO> findRanking() {
		List<RankingComprasDTO>res=comprasRepository.findRanking();
		return res;
	}
	
	public Compras saveCompras(Compras compras) {
		return comprasRepository.save(compras);
	}
    
	public Compras updateCompras(Integer id,Compras newCompras) {
		return comprasRepository.findById(id).map(c->{
            c.setTotalCompra(newCompras.getTotalCompra());
            c.setDataCompra(newCompras.getDataCompra());
            Compras atualizado=comprasRepository.save(c);
			return atualizado;
		}).orElse(null);
	}
	public void deleteCompras(Integer id) {
		if (comprasRepository.findById(id) != null)
			comprasRepository.deleteById(id);
	}
	
	public Compras fromDTO(ComprasDTO comprasDTO) {
		Compras entidade=new Compras(0,comprasDTO.getTotalCompra(),comprasDTO.getDataCompra(),
				new Cliente(comprasDTO.getCliente().getId(),null,null));
		return entidade;
	}
	
	
}
