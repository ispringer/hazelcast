package com.hazelcast.map.operation;

import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.map.MapEntrySet;
import com.hazelcast.map.RecordStore;
import com.hazelcast.map.record.Record;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.impl.QueryEntry;
import com.hazelcast.spi.BackupAwareOperation;
import com.hazelcast.spi.Operation;
import com.hazelcast.spi.PartitionAwareOperation;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

/**
 * date: 9/16/13
 * author: eminn
 */
public class PartitionWideEntryWithPredicateOperation extends PartitionWideEntryOperation {
    Predicate predicate;

    public PartitionWideEntryWithPredicateOperation() {
    }

    public PartitionWideEntryWithPredicateOperation(String name, EntryProcessor entryProcessor, Predicate predicate) {
        super(name, entryProcessor);
        this.predicate = predicate;
    }

    @Override
    protected Predicate getPredicate() {
        return predicate;
    }

    @Override
    protected void readInternal(ObjectDataInput in) throws IOException {
        super.readInternal(in);
        predicate = in.readObject();
    }

    @Override
    protected void writeInternal(ObjectDataOutput out) throws IOException {
        super.writeInternal(out);
        out.writeObject(predicate);
    }

    @Override
    public String toString() {
        return "PartitionWideEntryWithPredicateOperation{}";
    }

    @Override
    public Operation getBackupOperation() {
        EntryBackupProcessor backupProcessor = entryProcessor.getBackupProcessor();
        return backupProcessor != null ? new PartitionWideEntryWithPredicateBackupOperation(name, backupProcessor, predicate) : null;
    }
}
