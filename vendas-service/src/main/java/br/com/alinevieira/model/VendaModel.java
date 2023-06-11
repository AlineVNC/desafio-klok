package br.com.alinevieira.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.alinevieira.model.enums.VendaStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_venda")
public class VendaModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String cpfComprador;
	private LocalDateTime data;
	private LocalDateTime dataPagamento;
	
	@Enumerated(EnumType.STRING)
	private VendaStatus status;
		
	@OneToMany(mappedBy = "venda")
	private List<ItemModel> items;
	
	
	public VendaModel() {
		this.data = LocalDateTime.now(); 
		this.status = VendaStatus.CRIADA;
		this.items = new ArrayList<>();
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getCpfComprador() {
		return cpfComprador;
	}
	
	public void setCpfComprador(String cpfComprador) {
		this.cpfComprador = cpfComprador;
	}
	
	public LocalDateTime getData() {
		return data;
	}
	
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	public List<ItemModel> getItems() {
		return items;
	}
	
	public void setItems(List<ItemModel> items) {
		this.items = items;
	}

	public VendaStatus getStatus() {
		return status;
	}

	public void setStatus(VendaStatus status) {
		this.status = status;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public BigDecimal getTotal() {
		BigDecimal total = new BigDecimal(0);
		for (ItemModel item : this.items) {
			BigDecimal itemSubTotal = item.getSubTotal();
			total = total.add(itemSubTotal);
		}
		
		return total;		
	}
}
