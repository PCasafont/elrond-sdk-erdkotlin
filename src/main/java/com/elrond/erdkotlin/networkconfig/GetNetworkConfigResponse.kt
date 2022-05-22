package com.elrond.erdkotlin.networkconfig

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetNetworkConfigResponse(
    val config: NetworkConfigData
) {
    @Serializable
    internal data class NetworkConfigData(
        @SerialName("erd_chain_id")
        val chainID: String,
        @SerialName("erd_denomination")
        val erdDenomination: Int,
        @SerialName("erd_gas_per_data_byte")
        val gasPerDataByte: Int,
        @SerialName("erd_gas_price_modifier")
        val erdGasPriceModifier: Double,
        @SerialName("erd_latest_tag_software_version")
        val erdLatestTagSoftwareVersion: String,
        @SerialName("erd_meta_consensus_group_size")
        val erdMetaConsensusGroupSize: Long,
        @SerialName("erd_min_gas_limit")
        val minGasLimit: Long,
        @SerialName("erd_min_gas_price")
        val minGasPrice: Long,
        @SerialName("erd_min_transaction_version")
        val minTransactionVersion: Int,
        @SerialName("erd_num_metachain_nodes")
        val erdNumMetachainNodes: Long,
        @SerialName("erd_num_nodes_in_shard")
        val erdNumNodesInShard: Long,
        @SerialName("erd_num_shards_without_meta")
        val erdNumShardsWithoutMeta: Int,
        @SerialName("erd_rewards_top_up_gradient_point")
        val erdRewardsTopUpGradientPoint: String,
        @SerialName("erd_round_duration")
        val erdRoundDuration: Long,
        @SerialName("erd_rounds_per_epoch")
        val erdRoundsPerEpoch: Long,
        @SerialName("erd_shard_consensus_group_size")
        val erdShardConsensusGroupSize: Long,
        @SerialName("erd_start_time")
        val erdStartTime: Long,
        @SerialName("erd_top_up_factor")
        val erdTopUpFactor: String
    )
}
