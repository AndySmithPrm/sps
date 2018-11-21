package bz.sps;

/*
 * абстракция сервиса данных, чтобы если что его можно было заменить
 */
public interface StoredProcService {
	public String first(ProcParams params);
	public SecondProcResult second(ProcParams params);
}
