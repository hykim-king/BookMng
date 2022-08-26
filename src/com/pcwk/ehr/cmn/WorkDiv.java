package com.pcwk.ehr.cmn;

import java.util.List;

/**
 * DAO 표준화 interface
 * @author ITSC
 *
 */
public interface WorkDiv {
	/**
	 * 목록조회
	 * @param dto
	 * @return List<DTO>
	 */
	public abstract List<DTO> doRetrieve(DTO dto);

	/**
	 * 등록
	 * @param DTO
	 * @return 1(성공)/0(실패)
	 */
	public abstract int doSave(DTO dto);


	/**
	 * 수정
	 * @param DTO
	 * @return 1(성공)/0(실패)
	 */
	public int doUpdate(DTO dto);
	
	/**
	 * 삭제
	 * @param DTO
	 * @return 1(성공)/0(실패)
	 */
	int doDelete(DTO dto);
	

	//단건조회
	/**
	 * 단건조회 
	 * @param DTO
	 * @return DTO
	 */
	DTO doSelectOne(DTO obj);
	
	
	
	
	
	
	
	
	
	
	//파일저장
	//파일읽기
}
