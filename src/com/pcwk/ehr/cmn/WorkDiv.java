package com.pcwk.ehr.cmn;

import java.util.List;

/**
 * DAO 표준화 interface
 * @author ITSC
 *
 */
public interface WorkDiv<T> {
	/**
	 * 목록조회
	 * @param dto
	 * @return List<DTO>
	 */
	public abstract List<T> doRetrieve(DTO dto);

	/**
	 * 등록
	 * @param DTO
	 * @return 1(성공)/0(실패)
	 */
	public abstract int doSave(T dto);


	/**
	 * 수정
	 * @param DTO
	 * @return 1(성공)/0(실패)
	 */
	public int doUpdate(T dto);
	
	/**
	 * 삭제
	 * @param DTO
	 * @return 1(성공)/0(실패)
	 */
	int doDelete(T dto);
	

	//단건조회
	/**
	 * 단건조회 
	 * @param DTO
	 * @return DTO
	 */
	T doSelectOne(T obj);
	
	
	
	//파일저장
	//파일읽기
}
