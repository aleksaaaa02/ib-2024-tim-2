package rs.ac.uns.ftn.Bookify.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.PricelistItem;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IAccommodationRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AccommodationRepository implements IAccommodationRepository{
    @Override
    public void flush() {
    }

    @Override
    public <S extends Accommodation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Accommodation> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Accommodation> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Accommodation getOne(Long aLong) {
        return null;
    }

    @Override
    public Accommodation getById(Long aLong) {
        return null;
    }

    @Override
    public Accommodation getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Accommodation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Accommodation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Accommodation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Accommodation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Accommodation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Accommodation> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Accommodation, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Accommodation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Accommodation> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Accommodation> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Accommodation> findAll() {
        return null;
    }

    @Override
    public List<Accommodation> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Accommodation entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Accommodation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Accommodation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Accommodation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Collection<Accommodation> findByLocationAndGuestRange(String location, int persons, Date begin, Date end) {
        return null;
    }

    @Override
    public long countByLocationAndGuestRange(String location, int persons, Date begin, Date end) {
        return 0;
    }

    @Override
    public Optional<Double> findPriceForDay(Date date, Long accommodationId) {
        return Optional.empty();
    }
}
