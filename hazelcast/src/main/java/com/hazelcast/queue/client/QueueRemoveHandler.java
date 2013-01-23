/*
 * Copyright (c) 2008-2010, Hazel Ltd. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.hazelcast.queue.client;

import com.hazelcast.client.ClientCommandHandler;
import com.hazelcast.instance.Node;
import com.hazelcast.nio.Protocol;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.queue.QueueService;
import com.hazelcast.queue.proxy.DataQueueProxy;

public class QueueRemoveHandler extends QueueCommandHandler {
    public QueueRemoveHandler(QueueService queueService) {
        super(queueService);
    }

    @Override
    public Protocol processCall(Node node, Protocol protocol) {
        String name = protocol.args[0];
        Data item = protocol.buffers[0];
        DataQueueProxy queue = qService.createDistributedObjectForClient(name);
        boolean removed = queue.remove(item);
        return protocol.success(String.valueOf(removed));
    }
}