package kh.test.jdbckh.board.model.dao;

import static kh.test.jdbckh.common.jdbc.JdbcTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kh.test.jdbckh.board.model.vo.AttachFileVo;
import kh.test.jdbckh.board.model.vo.BoardVo;


public class BoardDao {
	// 모든 행 읽기
	public List<AttachFileVo> selectAttachFileList(SqlSession sesson, int bno) {
		System.out.println("[Board Dao selectAttachFile]");
		List<AttachFileVo> result = sesson.selectList("boardMapper1.selectAttachFile1", bno);
		System.out.println("[Board Dao selectAttachFile] return:" + result);
		return result;
	}
	
	public BoardVo selectOne(SqlSession sesson, int bno) {
		System.out.println("[Board Dao selectOne]");
		BoardVo result = sesson.selectOne("boardMapper1.selectOne1", bno);
		System.out.println("[Board Dao selectOne] return:" + result);
		return result;
	}
	// 한 행 삽입 - BoardVo 자료형을 받아와야 함.
	public int insert(SqlSession sesson, BoardVo dto, int nextVal) {
		System.out.println("[Board Dao insert]");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		map.put("nextVal", nextVal);
		int result = sesson.insert("boardMapper1.insert1", map);
		System.out.println("[Board Dao insert] return:" + result);
		return result;
	}
	public int insertReply(Connection conn, BoardVo dto) {
		System.out.println("[Board Dao insertReply] dto:" + dto);
		int result = 0;
		String query = "insert into BOARD values (SEQ_BOARD_BNO.nextval, ?, ?, default, ?    , (select bref from board where bno=?)    , (select bre_level+1 from board where bno=?)    , (select bre_step+1 from board where bno=?)    )";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getBtitle());
			pstmt.setString(2, dto.getBcontent());
			pstmt.setString(3, dto.getMid());
			pstmt.setInt(4, dto.getBno());
			pstmt.setInt(5, dto.getBno());
			pstmt.setInt(6, dto.getBno());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		System.out.println("[Board Dao insertReply] return:" + result);
		return result;
	}
	// 한 행 수정 - BoardVo 또는 경우에 따라서 특정 컬럼값만 받아오기도 함.
	public int update(Connection conn, BoardVo dto) {
		System.out.println("[Board Dao update] dto:" + dto);
		int result = -1;  // update 경우 0도 정상 결과값일 수 있으므로 -1을 초기값
		String query = "update board set BRE_STEP = BRE_STEP + 1 where BRE_STEP > (select bre_step from board where bno=?)  and BREF = (select bref from board where bno=?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, dto.getBno());
			pstmt.setInt(2, dto.getBno());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		System.out.println("[Board Dao update] return:" + result);
		return result;
	}

	// 한 행 삭제 - 주로 PK로 where조건
	public int delete(Connection conn, int bno) {
		System.out.println("[Board Dao delete] bno:" + bno);
		int result = 0;
		// TODO
		System.out.println("[Board Dao delete] return:" + result);
		return result;
	}

	//// 추가
	// 페이징 처리(pageSize!=0아닐때) + 검색(있다면 where처리)
	public int getTotalCount(Connection conn, String searchWord) {
		System.out.println("[Board Dao getTotalCount] searchWord:" + searchWord);

		int result = 0;
		String query = "select count(*) cnt from board ";
		if (searchWord != null) { // 검색(있다면 where처리)
			query += " where btitle like ? or bcontent like ? or mid like ? ";
			searchWord = "%" + searchWord + "%";
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(query);
			if (searchWord != null) { // 검색(있다면 where처리)
				pstmt.setString(1, searchWord);
				pstmt.setString(2, searchWord);
				pstmt.setString(3, searchWord);
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("[Board Dao getTotalCount] return:" + result);
		return result;
	}

	public List<BoardVo> selectList(Connection conn, int currentPage, int pageSize, int totalCount,
			String searchWord) {
		System.out.println("[Board Dao selectList] currentPage:" + currentPage + ",pageSize:" + pageSize + ",totalCount:"
				+ totalCount + ",searchWord:" + searchWord);

		// 페이징처리 방정식
		int startRownum = 0;
		int endRownum = 0;
		if (pageSize > 0) { // 페이징 처리(pageSize!=0아닐때)
			startRownum = (currentPage - 1) * pageSize + 1;
			endRownum = ((currentPage * pageSize) > totalCount) ? totalCount : (currentPage * pageSize);
			System.out.println("startRownum:" + startRownum + ", endRownum:" + endRownum);
		}

		List<BoardVo> result = new ArrayList<BoardVo>();
		String subquery = " select BNO, BTITLE, to_char(BWRITE_DATE, 'yyyy-mm-dd hh24:mi:ss') BWRITE_DATE, MID, BREF, BRE_LEVEL, BRE_STEP from BOARD ";
		if (searchWord != null) { // 검색(있다면 where처리)
			subquery += " where BTITLE like ? or BCONTENT like ? or MID like ? ";
			searchWord = "%" + searchWord + "%";
		}
		subquery += " order by BREF desc, BRE_STEP asc"; // 정렬순서
		String query = subquery;
		if (pageSize > 0) { // 페이징 처리(pageSize!=0아닐때)
			query = " select * from ( select ROWNUM rnum, tb1.* from " + "(" + subquery + ") tb1 " + " )";
			query += " where rnum between ? and ?";
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(query);
			if (searchWord != null) { // 검색(있다면 where처리)
				pstmt.setString(1, searchWord);
				pstmt.setString(2, searchWord);
				pstmt.setString(3, searchWord);
			}
			if (searchWord != null && pageSize > 0) { // 검색(있다면 where처리) // 페이징 처리(pageSize!=0아닐때)
				pstmt.setInt(4, startRownum);
				pstmt.setInt(5, endRownum);
			} else if (searchWord == null && pageSize > 0) {// 페이징 처리(pageSize!=0아닐때)
				pstmt.setInt(1, startRownum);
				pstmt.setInt(2, endRownum);
			}
			rs = pstmt.executeQuery();

			while (rs.next() == true) {
//				BNO         NOT NULL NUMBER         
//				BTITLE      NOT NULL VARCHAR2(300)  
//				BCONTENT             VARCHAR2(4000) 
//				BWRITE_DATE NOT NULL TIMESTAMP(6)   
//				MID         NOT NULL VARCHAR2(20)   
//				BREF        NOT NULL NUMBER         
//				BRE_LEVEL   NOT NULL NUMBER         
//				BRE_STEP    NOT NULL NUMBER
				BoardVo dto = new BoardVo(
						rs.getInt("BNO"),
						rs.getString("BTITLE"),
						rs.getString("BWRITE_DATE"),
						rs.getString("MID"),
						rs.getInt("BREF"),
						rs.getInt("BRE_LEVEL"),
						rs.getInt("BRE_STEP")					
						);
				result.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		System.out.println("[Board Dao selectList] return:" + result);
		return result;
	}
}
